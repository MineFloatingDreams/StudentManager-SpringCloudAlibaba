<template>
  <div class="student_table_container">
    <div class="toolBar">
      <el-button type="primary" @click="addStudent">添加学生</el-button>
      <el-button type="danger" @click="deleteStudentByIds">删除学生</el-button>
      <div class="search">
        <el-input placeholder="请输入学生姓名" class="input-with-select" v-model="searchName" clearable>
          <el-select v-model="select" slot="prepend" placeholder="请选择">
            <el-option label="学号" sortable="custom" value="id" min-width="300"></el-option>
            <el-option label="姓名" sortable="custom" value="name" min-width="100"></el-option>
            <el-option label="年龄" sortable="custom" value="age"></el-option>
            <el-option label="性别" sortable="custom" value="sex"></el-option>
            <el-option label="班级名称" sortable="custom" value="clazzName"></el-option>
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click="searchStudent"></el-button>
          <el-button slot="append" icon="el-icon-refresh" @click="resetSearch"></el-button>
        </el-input>
      </div>
    </div>

    <div class="student_table">
      <el-table :data="studentList" height="95%" border style="width: 100%;"
                @selection-change="handleSelectionChange" @sort-change="sortChange"
                v-loading="loading">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="学号" min-width="150"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="age" label="年龄"></el-table-column>
        <el-table-column prop="sex" :formatter="formatSex" label="性别"></el-table-column>
        <el-table-column prop="className" label="班级" min-width="150"></el-table-column>
        <el-table-column prop="classId" label="班级编号"></el-table-column>
        <el-table-column label="操作" fixed="right" min-width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="deleteStudent(scope.row.id)">删除</el-button>
            <el-button type="text" size="small" @click="updateStudent(scope.row)">修改</el-button>
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

    <el-dialog title="学生信息" :visible.sync="dialogFormVisible" width="40%">
      <student-form @closeMyDialog="closeMyDialog"
                    @initTable="initTable"
                    :update="update"
                    v-if="dialogFormVisible"></student-form>
    </el-dialog>
  </div>
</template>

<script>
import qs from "qs";
import {deleteStudentRequest, getStudentInfoRequest} from "@/api/student";
import StudentForm from "@/views/Student/templates/StudentForm";

export default {
  name: "Student",
  components: {StudentForm},
  data() {
    return {
      // 表格数据
      studentList: [],
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
        name: "",
        age: '',
        sex: '',
        classId: '',
        className: '',
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
      getStudentInfoRequest(this.token, this.searchName, this.select,
          this.currentPage, this.pageSize, this.sort.prop, asc)
          .then(res => {
            console.log(res)
            if (res.code === 200) {
              this.studentList = []
              this.total = Number.parseInt(res.data.totalNum)
              this.studentList = JSON.parse(res.data.student);
              this.totalPage = Number.parseInt(res.data.totalPages)
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
    deleteStudent(id) {
      this.loading = true;
      deleteStudentRequest(this.token, qs.stringify({ids: [id]}, {indices: false})).then(res => {
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
    updateStudent(row) {
      console.log(row)
      this.update = row;
      this.dialogFormVisible = true;
    },
    searchStudent() {
      if (this.select === "sex") {
        if (this.searchName === '男') {
          this.searchName = 'm'
        } else {
          this.searchName = 'f'
        }
      }
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
    deleteStudentByIds() {
      this.loading = true;
      deleteStudentRequest(this.token,
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
    addStudent() {
      this.update = {id: '', name: ''};
      this.dialogFormVisible = true;
    },
    formatSex(row, column, cellValue, index) {
      if (cellValue === 'm') {
        return '男'
      } else {
        return '女'
      }
    },
  },
}
</script>

<style scoped>
.student_table_container {
  width: 95%;
  height: 95%;
  margin: 2.5% auto;
}

.student_table_container > .toolBar > .search {
  float: right;
  margin-right: 10px;
}

.student_table_container > .student_table {
  margin-top: 20px;
  height: 80%;
}

.student_table_container > .table_page {
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