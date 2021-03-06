<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/commonPublic.jsp"%>
<html>
<script type="text/javascript">
	function check() {
		var $phone = $("input[name='phone']");
		if ($phone.val() == "") {
			$phone.tips({
				side : 2,
				msg : '手机号不得为空',
				bg : '#AE81FF',
				time : 3
			});

			$phone.focus();
			return false;
		} else {
			$phone.val(jQuery.trim($phone.val()));
		}
		if($("input[name='password']").val() != $("input[name='password2']").val()) {
			$("input[name='password']").tips({
				side:2,
	            msg:'两次密码不一致',
	            bg:'#AE81FF',
	            time:3
	        });
	        return false;
		}
		return true;
	}
	// 保存用户信息
	function saveCustomer() {
		if (check()) {
			var form = $("#customerInfoForm");
			var options = {
				url : 'customer!updateAjax', //提交给哪个执行
				type : 'POST',
				success : function(result) {
					//从返回的json数据中获取结果信息
					//结果提示信息
					var appResult = eval("(" + result + ")");
					var message = appResult.error_info;
					var success = appResult.result;
					if (success != "SUCCESS" ) {
						$("#save").tips({
							side : 2,
							msg : message,
							bg : '#68B500',
							time : 10
						});
					} else {
						pageGo('customer.action');
					}
				} //显示操作提示
			};
			form.ajaxSubmit(options);
		}
	}
</script>
  <body>
    <ul class="breadcrumb">
		<li><a href="user!index">主页</a> <span class="divider">/</span></li>
		<li>客户列表 <span class="divider">/</span>
		</li>
		<li class="active">修改客户信息：</li>
	</ul>

    
    <form method="post" id="customerInfoForm" enctype="multipart/form-data">
    <input name="customer_id" value="<s:property value="customer_id" />"
			type="hidden" /> 
			<input name="phone" value="<s:property value="phone" />"
			type="hidden" /> 
    <table border="3" bordercolor="blue" width="40%" cellspacing="0" cellpadding="0" >
    <tr>
    <font style="color:red">如不需修改密码请不要填写</font>
    <td>用户手机号：</td>
    <td><input type="text" name="phone" disabled="disabled" value="<s:property value="phone" />"/></td>
    </tr>
    <tr>
    <td>名称：</td>
    <td><input type="text" name="name" value="<s:property value="name" />"/></td>
    </tr>
    <tr>
    <td>原始头像：</td>
    <td><img alt="客户头像" src="<s:property value="image" />" height="200" width="200" />
    </td>
    </tr>
    <tr>
    <td>修改头像：</td>
    <td>
    <input name="image" type="file" src=""/>
    </td>
    </tr>
     <tr>
    <td>性别：</td>
    <td><input type="radio" name="sex" value="1"  <s:if test="sex==1" >checked="checked"</s:if>/>男  <input type="radio" name="sex" value="0" <s:if test="sex==0" >checked="checked"</s:if> /> 女 </td>
    </tr>
     <tr>
    <td>修改密码：</td>
    <td><input name="password" type="text" value=""/></td>
    </tr>
     <tr>
    <td>确认密码：</td>
    <td><input name="password2" type="text" /></td>
    </tr>
     <tr>
    <td>出生日期：</td>
    <td><input name="birth_day" type="text"  value="<s:property value="birth_day" />"/></td>
    </tr>
     <tr>
    <td>上次登录时间：</td>
    <td> <s:date format="yyyy-MM-dd HH:mm:ss" name="last_login_time" />  </td>
    </tr>
     <tr>
    <td colspan="2"><input  type="radio" value="0" name="isLock" <s:if test="isLock==0" >checked="checked"</s:if> />未锁定 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" value="1" name="isLock" <s:if test="isLock==1" >checked="checked"</s:if> />已锁定</td>
    </tr>
     <tr>
    <td colspan="2"><input type="radio" name="deleted" value="0" <s:if test="deleted==0" >checked="checked"</s:if> />未删除&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="deleted" value="1" <s:if test="deleted==1" >checked="checked"</s:if>/> 已删除</td>
    </tr>
    <tr>
    <td colspan="2" align="center"><button class="btn" type="button"
						onclick="saveCustomer()" id="save">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button class="btn" type="button"
						onclick="pageGo('customer.action')">返回</button></td>
    </tr>
    </table>
    
    </form>
  </body>
</html>
