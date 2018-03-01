<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>查看员工</title>
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox-content">
    <validator name="validation">
      <form name="createMirror" novalidate class="form-horizontal" role="form">

        <div class="text-center">
          <h3>个人名片</h3>
        </div>
        <hr/>
        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">姓名</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.name"></label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">员工编号</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.orgCode"></label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">职务</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.position"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <img :src="employee.photo" alt="" style="width: 105px;margin-left: 84px">
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">手机号</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.mobile"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">邮箱</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.email"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">入职日期</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.entryDate"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">性别</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.gender | gender-filter"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">部门</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.department"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">公司</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.company"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="text-center">
          <h3>其他信息</h3>
        </div>
        <hr/>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">最高学历</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.education"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">职称</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.title"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">籍贯</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.nativePlace"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">身份证号</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.idNum"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">民族</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.nation"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">政治面貌</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.politicsStatus"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">户口所在地</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.censusAddress"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">户口性质</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.censusNature | censusnature-filter"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">现住址</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.presentAddress"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">婚姻状态</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.maritalStatus | maritalstatus-filter"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">出生日期</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.birthday"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">英语水平</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.englishLevel | englishlevel-filter"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">用工类型</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.types | types-filter"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="text-center">
          <h3>紧急联系人</h3>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">姓名</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.linkman1"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">电话</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.linkphone1"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">姓名</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.linkman2"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">电话</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.linkphone2"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="text-center">
          <h3>教育经历</h3>
        </div>
        <hr/>
        <table id="eduDataTable" width="100%" class="table table-striped table-bordered table-hover">
        </table>

        <div class="text-center" style="margin-top: 20px">
          <h3>工作经历</h3>
        </div>
        <hr/>
        <table id="workDataTable" width="100%" class="table table-striped table-bordered table-hover">
        </table>

        <div class="text-center" style="margin-top: 20px">
          <h3>与原单位解除劳动关系证明</h3>
        </div>
        <hr/>

        <div class="col-md-12">
          <div class="form-group">
            <label class="col-md-1 control-label">
              <input disabled type="checkbox" v-model="employee.origProve" name="origProve">
            </label>
            <label class="text-left col-md-11" style="padding-top: 7px;">请提供同原单位解除劳动关系的证明文件;</label>
          </div>
        </div>

        <div class="col-md-12">
          <div class="form-group">
            <label class="col-md-1 control-label">
              <input disabled type="checkbox" v-model="employee.retireProve" name="retireProve">
            </label>
            <label class="text-left col-md-11" style="padding-top: 7px;">如在原单位已退休，请提供退休证明文件;</label>
          </div>
        </div>

        <div class="col-md-12">
          <div class="form-group">
            <label class="col-md-1 control-label">
              <input disabled readonly type="checkbox" v-model="employee.noProve" name="noProve">
            </label>
            <label class="text-left col-md-11" style="padding-top: 7px;">
              若无法提供解除劳动关系的证明文件，请在此声明：若因与原单位未解除劳动关系而产生的法律纠纷和经济补偿一概由本人承担，与本公司无关。
            </label>
          </div>
        </div>

        <br/>
        <br/>
        <div class="text-center">
          <button @click="cancel" type="button" class="btn btn-default">返回
          </button>
        </div>
      </form>
    </validator>
    <!-- ibox end -->
  </div>
</div>
<!-- container end-->
<script src="${ctx}/static/js/containers/employee/detail.js"></script>