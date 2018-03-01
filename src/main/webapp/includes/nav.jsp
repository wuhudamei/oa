<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!--左侧导航开始-->
<nav id="nav" class="navbar-default navbar-static-side"
     role="navigation">
    <div class="nav-close">
        <i class="fa fa-times-circle"></i>
    </div>
    <div id="navUser" class="sidebar-collapse">
        <div class="nav-header">
            <div>
                <div class="dropdown profile-element">
					<span> <img :src="user.headImg" class="img-circle"
                                width="60px" height="60px"/>
					</span> <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
                        class="clear"> <span class="block m-t-xs"> <strong
                        class="font-bold"><shiro:principal property="name"/></strong>
						</span> <span class="text-muted text-xs block"><shiro:principal
                        property="position"/> <%--<b class="caret"></b>--%> </span>
					</span>
                </a>
                    <%--<ul class="dropdown-menu animated fadeInRight m-t-xs">--%>
                    <%--<li><a class="J_menuItem" href="form_avatar.html">修改头像</a></li>--%>
                    <%--<li><a class="J_menuItem" href="profile.html">个人资料</a></li>--%>
                    <%--<li><a class="J_menuItem" href="contacts.html">联系我们</a></li>--%>
                    <%--<li><a class="J_menuItem" href="mailbox.html">信箱</a></li>--%>
                    <%--<li class="divider"></li>--%>
                    <%--<li><a href="login.html">安全退出</a></li>--%>
                    <%--</ul>--%>
                </div>
                <!--                 <div class="logo-element">暮涩</div> -->
            </div>
        </div>
        <!-- 左侧菜单 start-->
        <ul class="nav metismenu" id="sideMenu">
            <shiro:hasPermission name="index:list">
                <li id="dashboardMenu"><a href="/"> <i class="fa fa-home"></i>
                    <span class="nav-label">主页</span>
                </a></li>
            </shiro:hasPermission>

            <shiro:hasPermission name="system:manager">
                <li id="systemMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">系统管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="organization:list">
                            <li id="organizationMenu"><a href="/admin/orgList"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">组织架构</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="dictionary:list">
                            <li id="dictionaryMenu"><a href="/admin/dicList"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">数据字典</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="role:list">
                            <li id="role"><a href="/admin/roles"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">科目角色管理</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="subjectProcess:list">
                            <li id="subProcessManager"><a href="/admin/subprocess"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">科目流程管理</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="process:manager">
                            <li id="processManager"><a href="/admin/process"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">流程管理</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="system:systemParam">
                            <li id="systemParam"><a href="/admin/system/param"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">系统参数设置</span>
                            </a></li>
                        </shiro:hasPermission>

                    </ul>
                <li/>
            </shiro:hasPermission>

            <shiro:hasPermission name="regulation:menu">
                <li id="regulationMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">规章制度管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="regulation:list">
                            <li id="regulations"><a href="/admin/regulations"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">规章制度列表</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="regulation:add">
                            <li id="create"><a href="/admin/regulation/createOrUpdate">
                                <i class="fa fa-edit"></i> <span class="nav-label">创建规章制度</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="emp:menu">
                <li id="empManagerMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">员工管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="emp:list">
                            <li id="employeeList"><a href="/admin/employee/list"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">员工列表</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="emp:manager">
                            <li id="employeeManage"><a href="/admin/employee/management">
                                <i class="fa fa-edit"></i> <span class="nav-label">员工管理</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="emp:new">
                            <li id="employeeAdd"><a href="/admin/employee/create"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">新增员工</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="emp:outernew">
                            <li id="outerEmployeeAdd"><a href="/admin/employee/outercreate"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">新增外部员工</span>
                            </a></li>
                        </shiro:hasPermission>
                        <!--           <li id="contractManage"> -->
                        <!--             <a href="/admin/employeeContract"> -->
                        <!--               <i class="fa fa-edit"></i> -->
                        <!--               <span class="nav-label">合同提醒</span> -->
                        <!--             </a> -->
                        <!--           </li> -->
                    </ul>
                <li/>
            </shiro:hasPermission>

            <shiro:hasPermission name="approve:menu">
                <li id="myNeedDo"><a href="#"> <i
                        class="fa fa-edit"></i> <span class="nav-label">我的审批</span><span
                        class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="approval:waitApproval">
                            <li id="waitApproval"><a href="/admin/approval"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">待审批</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="approval:doApproval">
                            <li id="doApproval"><a href="/admin/doApproval"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">已审批</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="approval:ccUser">
                            <li id="ccUser"><a href="/admin/ccUser"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">抄送</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="leave:menu">
                <li id="leveAndBusinessMenu"><a href="#"> <i
                        class="fa fa-edit"></i> <span class="nav-label">我的申请</span> <span
                        class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="leave:apply">
                            <li id="myLeveApply"><a href="/admin/vacationAdd"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">请假申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="signature:apply">
                            <li id="signatureApply"><a href="/admin/applySignature"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">费用申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="payment:apply">
                            <li id="paymentApply"><a href="/admin/applyPayment"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">报销申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="business:apply">
                            <li id="myBusinessApply"><a href="/admin/businessAdd"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">出差申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="business:manager">
                            <li id="myLevAffair"><a
                                    href="/admin/businessAway/leaveAndBusiness"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">我的申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="business:common">
                            <li id="commonApplyCreate">
                                <a href="/admin/commonApply/create">
                                    <i class="fa fa-edit"></i> <span class="nav-label">通用申请</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                    </ul>
                <li/>
            </shiro:hasPermission>

            <shiro:hasPermission name="financailPayment:menu">
                <li id="financailPayment"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">财务报销</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="financailPayment:chairmanGrant">
                            <li id="chairmanGrant"><a
                                    href="/admin/financail/payment?type=GRANT"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">董事长授权</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="financailPayment:toReimbursed">
                            <li id="toReimbursed"><a
                                    href="/admin/financail/payment?type=TOREIMBURSED"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">待报销</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="financailPayment:reimbursed">
                            <li id="reimbursed"><a
                                    href="/admin/financail/payment/reimbursed"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">已报销</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="yearBudget:menu">
                <li id="yearBudgetMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">年度预算管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="yearBudget:administrator">
                            <li id="yearAdminBudgetApply"><a
                                    href="/admin/yearbudget/apply?type=ADMINISTRATOR"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">行政类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="yearBudget:matters">
                            <li id="yearMattersBudgetApply"><a
                                    href="/admin/yearbudget/apply?type=MATTERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">人事类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="yearBudget:marketing">
                            <li id="yearMarketingBudgetApply"><a
                                    href="/admin/yearbudget/apply?type=MARKETING"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">营销类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="yearBudget:customer">
                            <li id="yearCustomerBudgetApply"><a
                                    href="/admin/yearbudget/apply?type=CUSTOMER"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">客管类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="yearBudget:others">
                            <li id="yearOthersBudgetApply"><a
                                    href="/admin/yearbudget/apply?type=OTHERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">其他类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="yearBudget:manager">
                            <li id="yearBudgetAffair"><a href="/admin/yearbudget/list">
                                <i class="fa fa-edit"></i> <span class="nav-label">我的事务</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="monthBudget:menu">
                <li id="budgetMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">月度预算管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="monthBudget:administrator">
                            <li id="adminBudgetApply"><a
                                    href="/admin/applyBudget?type=ADMINISTRATOR"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">行政类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="monthBudget:matters">
                            <li id="mattersBudgetApply"><a
                                    href="/admin/applyBudget?type=MATTERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">人事类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="monthBudget:marketing">
                            <li id="marketingBudgetApply"><a
                                    href="/admin/applyBudget?type=MARKETING"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">营销类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="monthBudget:customer">
                            <li id="customerBudgetApply"><a
                                    href="/admin/applyBudget?type=CUSTOMER"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">客管类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="monthBudget:others">
                            <li id="othersBudgetApply"><a
                                    href="/admin/applyBudget?type=OTHERS"> <i class="fa fa-edit"></i>
                                <span class="nav-label">其他类预算申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="monthBudget:manager">
                            <li id="budgetAffair"><a href="/admin/budgets"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">我的事务</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="purchase:menu">
                <li id="PurchaseMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">采购申请管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="purchase:administrator">
                            <li id="adminPurchaseApply"><a
                                    href="/admin/purchase/add?type=ADMINISTRATOR"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">行政类采购申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="purchase:matters">
                            <li id="mattersPurchaseApply"><a
                                    href="/admin/purchase/add?type=MATTERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">人事类采购申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="purchase:marketing">
                            <li id="marketingPurchaseApply"><a
                                    href="/admin/purchase/add?type=MARKETING"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">营销类采购申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="purchase:customer">
                            <li id="customerPurchaseApply"><a
                                    href="/admin/purchase/add?type=CUSTOMER"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">客管类采购申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="purchase:others">
                            <li id="othersPurchaseApply"><a
                                    href="/admin/purchase/add?type=OTHERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">其他类采购申请</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="purchase:manager">
                            <li id="purchaseAffair"><a href="/admin/purchase/list">
                                <i class="fa fa-edit"></i> <span class="nav-label">我的事务</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>


            <shiro:hasPermission name="signature:menu">
                <li id="signatureMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">签报管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="signature:administrator">
                            <li id="adminSignatureApply"><a
                                    href="/admin/applySignature?type=ADMINISTRATOR"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">行政类签报申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="signature:matters">
                            <li id="mattersSignatureApply"><a
                                    href="/admin/applySignature?type=MATTERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">人事类签报申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="signature:marketing">
                            <li id="marketingSignatureApply"><a
                                    href="/admin/applySignature?type=MARKETING"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">营销类签报申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="signature:customer">
                            <li id="customerSignatureApply"><a
                                    href="/admin/applySignature?type=CUSTOMER"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">客管类签报申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="signature:others">
                            <li id="othersSignatureApply"><a
                                    href="/admin/applySignature?type=OTHERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">其他类签报申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="signature:manager">
                            <li id="signatureAffair"><a href="/admin/signatures"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">我的事务</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="payment:menu">
                <li id="paymentMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">费用报销管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="payment:administrator">
                            <li id="adminPaymentApply"><a
                                    href="/admin/applyPayment?type=ADMINISTRATOR"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">行政类报销申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="payment:matters">
                            <li id="mattersPaymentApply"><a
                                    href="/admin/applyPayment?type=MATTERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">人事类报销申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="payment:marketing">
                            <li id="marketingPaymentApply"><a
                                    href="/admin/applyPayment?type=MARKETING"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">营销类报销申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="payment:customer">
                            <li id="customerPaymentApply"><a
                                    href="/admin/applyPayment?type=CUSTOMER"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">客管类报销申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="payment:others">
                            <li id="othersPaymentApply"><a
                                    href="/admin/applyPayment?type=OTHERS"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">其他类报销申请</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="payment:manager">
                            <li id="paymentAffair"><a href="/admin/payments"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">我的事务</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="process:monitored">
                <li id="monitored"><a href="/admin/processMonitored"> <i
                        class="fa fa-edit"></i> <span class="nav-label">流程监控</span>
                </a>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="wechat:menu">
                <li id="wechatMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">微信管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="wechat:tagmanager">
                            <li id="tagMenu"><a href="/admin/wechat/tag"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">标签管理</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="wechat:custommenu">
                            <li id="customMenu"><a href="/admin/wechat/menu"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">自定义菜单</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="wechat:conditionalmenu">
                            <li id="conditionalMenu"><a
                                    href="/admin/wechat/menu/condition"> <i class="fa fa-edit"></i>
                                <span class="nav-label">个性化菜单</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="wechat:qrcodemenu">
                            <li id="qrcodeMenu"><a href="/admin/wechat/qrcode/list">
                                <i class="fa fa-edit"></i> <span class="nav-label">生成二维码</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="wechat:message">
                            <li id="wxMessage">
                                <a href="/admin/wechat/messageLog">
                                    <i class="fa fa-edit"></i> <span class="nav-label">微信消息</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="stylist:menu">
                <li id="designerMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">设计师提成管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="stylist:rules">
                            <li id="ruleSetup"><a href="/admin/stylist/rules"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">规则设置</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="stylist:manager">
                            <li id="designerManager"><a href="/admin/stylists"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">设计师管理</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="stylist:assessment">
                            <li id="assessmentManager"><a href="/admin/evaluate"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">考核管理</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="stylist:contract">
                            <li id="contractManager"><a href="/admin/stylist/contracts">
                                <i class="fa fa-edit"></i> <span class="nav-label">合同确认</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="stylist:bill">
                            <li id="stylistBillManager"><a
                                    href="/admin/stylist/monthBills"> <i class="fa fa-edit"></i>
                                <span class="nav-label">提成账单</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="work:menu">
                <li id="workMenu"><a href="#"> <i class="fa fa-edit"></i> <span
                        class="nav-label">考勤管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="work:sign">
                            <li id="sign"><a href="/admin/sign"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">上班/下班</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="work:signsite">
                            <li id="signsite"><a href="/admin/sign/signsite"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">职场设置</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="work:workstatistics">
                            <li id="workstatistics"><a href="/admin/sign/checkonwork">
                                <i class="fa fa-edit"></i> <span class="nav-label">考勤统计</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="work:signtimeset">
                            <li id="signtimeset"><a href="/admin/signTimeSet"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">设置上下班时间</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="work:removebinding">
                            <li id="removebinding"><a href="/admin/remove"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">解绑</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="work:comcheckonwork">
                            <li id="comcheckonwork"><a href="/admin/sign/comcheckonwork">
                                <i class="fa fa-edit"></i> <span class="nav-label">分公司考勤统计</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="content:menu">
                <li id="contentMenu"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">内容管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                            <%--<shiro:hasPermission name="content:message:send">--%>
                            <%--<li id="sendWechat">--%>
                            <%--<a href="">--%>
                            <%--<i class="fa fa-edit"></i>--%>
                            <%--<span class="nav-label">发微信消息</span>--%>
                            <%--</a>--%>
                            <%--</li>--%>
                            <%--</shiro:hasPermission>--%>

                            <%--<shiro:hasPermission name="content:message:list">--%>
                            <%--<li id="wechatList">--%>
                            <%--<a href="">--%>
                            <%--<i class="fa fa-edit"></i>--%>
                            <%--<span class="nav-label">微信消息列表</span>--%>
                            <%--</a>--%>
                            <%--</li>--%>
                            <%--</shiro:hasPermission>--%>

                        <shiro:hasPermission name="content:notice:public">
                            <li id="noticeboard"><a href="/admin/noticeboard"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">发公告通知</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="content:manager:notice">
                            <li id="mnoticeboard"><a href="/admin/mannouncenotice">
                                <i class="fa fa-edit"></i> <span class="nav-label">董事长公告</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="content:notice:list">
                            <li id="noticeList"><a href="/admin/noticeboard/noticeboard">
                                <i class="fa fa-edit"></i> <span class="nav-label">公告通知列表</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="signOrder:manager">
                <li id="singleSign"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">签报单管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="signOrder:companySign">
                            <li id="companySign"><a href="/admin/singleSign"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">公司签报单</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="signOrder:departmentSign">
                            <li id="departmentSign"><a href="/admin/singleSign/add">
                                <i class="fa fa-edit"></i> <span class="nav-label">部门签报单</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="report:Menu">
                <li id="reportManager"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">报表管理</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="report:geekReport">
                            <li id="grathercustomerReport"><a
                                    href="/api/grathercustomer"> <i class="fa fa-edit"></i> <span
                                    class="nav-label">集客报表</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:reportCenter">
                            <li id="allReport"><a href="/api/reports/list"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">报表中心</span>
                            </a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="report:groupNissin">
                            <li id="groupNissin"><a href="/api/report/groupNissin"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">集团日清报表</span>
                            </a></li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="report:depNissin">
                            <li id="depNissin"><a href="/api/report/depNissin"> <i
                                    class="fa fa-edit"></i> <span class="nav-label">分店日清报表</span>
                            </a></li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="accountChecking:Menu">
                <li id="materialBill"><a href="#"> <i class="fa fa-edit"></i>
                    <span class="nav-label">材料商对账</span> <span class="fa arrow"></span>
                </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <li id="noAccountCheckingOrder"><a
                                href="/admin/noAccountCheckingOrder"> <i class="fa fa-edit"></i>
                            <span class="nav-label">未对账订单</span>
                        </a></li>
                            <%--<li id="noAccountChecking">--%>
                            <%--<a href="/admin/materialSupplierAccountChecking">--%>
                            <%--<i class="fa fa-edit"></i>--%>
                            <%--<span class="nav-label">未对账</span>--%>
                            <%--</a>--%>
                            <%--</li>--%>
                        <li id="isAccountChecking"><a
                                href="/admin/alreadyMaterialSupplierAccountChecking"> <i
                                class="fa fa-edit"></i> <span class="nav-label">已对账</span>
                        </a></li>
                            <%--<li id="budget">--%>
                            <%--<a href="/admin/budget">--%>
                            <%--<i class="fa fa-edit"></i>--%>
                            <%--<span class="nav-label">预计订单合同额与成本统计</span>--%>
                            <%--</a>--%>
                            <%--</li>--%>
                            <%--<li id="hmCheckAndAccept">--%>
                            <%--<a href="/admin/HMCheckAndAccept">--%>
                            <%--<i class="fa fa-edit"></i>--%>

                            <%--<span class="nav-label">手动验收</span>--%>
                            <%--</a>--%>
                            <%--</li>--%>
                            <%--<li id="supplement">--%>
                            <%--<a href="/admin/supplement">--%>
                            <%--<i class="fa fa-edit"></i>--%>
                            <%--<span class="nav-label">信息补全</span>--%>
                            <%--</a>--%>
                            <%--</li>--%>
                            <%--<li id="statement">--%>
                            <%--<a href="/admin/statement">--%>
                            <%--<i class="fa fa-edit"></i>--%>
                            <%--<span class="nav-label">报表</span>--%>
                            <%--</a>--%>
                            <%--</li>--%>

                    </ul>
                </li>
            </shiro:hasPermission>

            <shiro:hasPermission name="user:resetPw">
                <li id="modifyPwd"><a href="/api/users/modifyPwd"> <i
                        class="fa fa-edit"></i> <span class="nav-label">修改密码</span>
                </a></li>
            </shiro:hasPermission>
            <!--总经理信箱-->
            <shiro:hasPermission name="chairmanMailbox:Menu">
                <li id="chairmanMailbox">
                    <a href="#"> <i class="fa fa-edit"></i>
                        <span class="nav-label">总经理信箱</span> <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <li id="addMail">
                            <a href="/admin/chairmanMailbox/add">
                                <i class="fa fa-edit"></i> <span class="nav-label">写信</span>
                            </a>
                        </li>
                        <li id="unReadList">
                            <a href="/admin/chairmanMailbox/list?readStatus=N">
                                <i class="fa fa-edit"></i> <span class="nav-label">未审阅</span>
                            </a>
                        </li>
                        <li id="readList">
                            <a href="/admin/chairmanMailbox/list?readStatus=Y">
                                <i class="fa fa-edit"></i> <span class="nav-label">已审阅</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasPermission>
            <!--竣工工地数据-->
            <shiro:hasPermission name="completionSiteData:Menu">
                <li id="completionSiteData">
                    <a href="#"> <i class="fa fa-edit"></i>
                        <span class="nav-label">竣工工地数据</span> <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <li id="list">
                            <a href="/admin/completionSiteData/list">
                                <i class="fa fa-edit"></i> <span class="nav-label">竣工工地数据</span>
                            </a>
                        </li>
                    </ul>
                </li>
            </shiro:hasPermission>
            <!--工资管理-->
            <shiro:hasPermission name="salaryManagement:Menu">
                <li id="chairmanMailbox">
                    <a href="#"> <i class="fa fa-edit"></i>
                        <span class="nav-label">工资管理</span> <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="salaryManagement:SalaPayroll">
                            <li id="listPayroll">
                                <a href="/admin/salaryManagement/listPayroll">
                                    <i class="fa fa-edit"></i> <span class="nav-label">工资单</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="salaryManagement:MySala">
                            <li id="listMySala">
                                <a href="/admin/salaryManagement/listMySala">
                                    <i class="fa fa-edit"></i> <span class="nav-label">我的工资</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>
            <!--工资管理-->
        </ul>
        <!-- 左侧菜单 end-->
    </div>
</nav>
<!--左侧导航结束-->