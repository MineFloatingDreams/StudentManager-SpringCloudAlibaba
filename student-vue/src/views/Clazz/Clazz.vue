<template>
  <div class="clazz_table_container">
    <div class="toolBar">
      <el-button type="primary" @click="addClass">添加班级</el-button>
      <el-button type="danger" @click="deleteClassByIds">删除班级</el-button>
      <div class="search">
        <el-input placeholder="请输入搜索内容" class="input-with-select" v-model="searchName" clearable>
          <el-select v-model="select" slot="prepend" placeholder="请选择">
            <el-option label="班级名称" value="clazzName"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click="searchClass"></el-button>
          <el-button slot="append" icon="el-icon-refresh" @click="resetSearch"></el-button>
        </el-input>
      </div>
    </div>

    <div class="clazz_table">
      <el-table :data="classList" height="95%" border style="width: 100%;"
                @selection-change="handleSelectionChange" @sort-change="sortChange"
                v-loading="loading">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" sortable="custom" label="编号" min-width="100"></el-table-column>
        <el-table-column prop="clazzName" sortable="custom" label="名称" min-width="100"></el-table-column>
        <el-table-column label="操作" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteClass(scope.row.id)">删除</el-button>
            <el-button type="text" size="small" @click="updateClass(scope.row.id,scope.row.clazzName)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="table_page">
      <el-pagination
          :hide-on-single-page="pageHide"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="pageSize"
          :page-count="totalPage"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"></el-pagination>
    </div>

    <el-dialog title="班级信息" :visible.sync="dialogFormVisible" width="40%">
      <class-form @closeMyDialog="closeMyDialog"
                  @initTable="initTable"
                  :update="update"
                  v-if="dialogFormVisible"></class-form>
    </el-dialog>
  </div>

</template>

<script>
import {deleteClassRequest, getClassInfoRequest} from "@/api/class";
import qs from "qs";
import ClassForm from "@/views/Clazz/templates/ClassForm";

export default {
  name: "Clazz",
  components: {ClassForm},
  data() {
    return {
      // 表格数据
      classList: [],
      // 当前页
      currentPage: 0,
      // 每页显示条数
      pageSize: 10,
      // 总条数
      total: 0,
      // 总页数
      totalPage: 1,
      // 搜索名称
      searchName: '',
      // 搜索类型
      select: '',
      //复选框数据
      multipleSelection: [],
      //排序内容
      sort: {
        prop: 'id',
        order: 'ascending'
      },
      // 是否显示分页
      pageHide: false,
      // 是否显示加载
      loading: true,
      // 用户token
      token: "",
      // 添加班级对话框
      dialogFormVisible: false,
      //更新班级信息ID
      update: {
        id: "",
        name: ""
      }
    }
  },
  created() {
    this.initTable();
  },
  methods: {
    initTable() {
      this.token = localStorage.getItem("token");
      const asc = this.sort.order === "ascending";
      this.loading = true;
      if (this.select === '' && this.searchName !== '') {
        this.$message({
          message: '请选择搜索类型',
          type: 'warning'
        });
        this.loading = false;
        return;
      }
      getClassInfoRequest(this.token, this.searchName, this.select,
          this.currentPage, this.pageSize, this.sort.prop, asc)
          .then(res => {
            if (res.code === 200) {
              this.classList = []
              this.total = res.data.totalNum
              this.classList = JSON.parse(res.data.clazz);
              this.totalPage = res.data.totalPages
              this.pageHide = this.totalPage === 1;
              this.loading = false
              this.$message({
                message: res.message,
                type: 'success'
              });
            } else {
              this.$message({
                message: "发生错误，错误信息：" + res.code + res.message,
                type: 'error'
              });
              this.loading = false;
            }
          })
          .catch(err => {
            console.log(err)
            this.loading = false
            this.$message.error('发生错误，错误信息：' + err)

          })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteClass(id) {
      this.loading = true;
      deleteClassRequest(this.token, qs.stringify({ids: [id]}, {indices: false})).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.initTable();
        } else {
          this.$message({
            message: '删除失败，错误信息:' + res.code + res.message,
            type: 'error'
          })
        }
        this.initTable()
      }).catch(err => {
        this.$message({
          message: '删除失败，错误信息:' + err,
          type: 'error'
        })
      })
    },
    updateClass(id, name) {
      this.update.id = id;
      this.update.name = name;
      this.dialogFormVisible = true;
    },
    searchClass() {
      this.initTable();
    },
    resetSearch() {
      this.searchName = '';
      this.select = ''
      this.sort = {prop: 'id', order: 'ascending'}
      this.initTable();
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.initTable();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initTable();
    },
    sortChange(row) {
      this.sort.order = row.order;
      this.sort.prop = row.prop;
      this.initTable()
    },
    deleteClassByIds() {
      this.loading = true;
      deleteClassRequest(this.token,
          qs.stringify({ids: this.multipleSelection.map(item => item.id)}, {indices: false})).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.initTable();
        } else {
          this.$message({
            message: '删除失败，错误信息:' + res.code + res.message,
            type: 'error'
          })
        }
        this.initTable()
      }).catch(err => {
        this.$message({
          message: '删除失败，错误信息:' + err,
          type: 'error'
        })
      })
    },
    closeMyDialog() {
      this.dialogFormVisible = false;
    },
    addClass() {
      this.update = {id: '', name: ''};
      this.dialogFormVisible = true;
    }
  },

}
</script>

<style scoped>
.clazz_table_container {
  width: 95%;
  height: 95%;
  margin: 2.5% auto;
}

.clazz_table_container > .toolBar > .search {
  float: right;
  margin-right: 10px;
}

.clazz_table_container > .clazz_table {
  margin-top: 20px;
  height: 80%;
}

.clazz_table_container > .table_page {
  margin-top: 10px;
  text-align: right;
}

.el-select {
  width: 120px;
}

.input-with-select {
  width: 400px;
}

</style>