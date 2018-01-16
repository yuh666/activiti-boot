<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/js/commons.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假任务办理</title>
</head>
<body>
 	<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		        <td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		          <tr>
		            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="6%" height="19" valign="bottom"><div align="center"><img src="${pageContext.request.contextPath }/images/tb.gif" width="14" height="14" /></div></td>
		                <td width="94%" valign="bottom"><span class="STYLE1">请假申请的任务办理</span></td>
		              </tr>
		            </table></td>
		            <td><div align="right"><span class="STYLE1">
		              </span></div></td>
		          </tr>
		        </table></td>
		      </tr>
		    </table></td>
		  </tr>
		  <tr>
		  	<td>
			  		<div align="left" class="STYLE21">
						请假天数:<input type="text" name="days" value="${leaveBill.days}" cssStyle="width: 200px;"/><br>
						请假原因:<input type="text" name="content" value="${leaveBill.content}" cssStyle="width: 200px;"/><br>
						请假备注:<textarea name="remark" cols="50" rows="5">${leaveBill.remark}</textarea><br/>
						批   注:<textarea name="comment" cols="50" rows="5"></textarea><br/>
						<input type="button" name="button" value="返回" class="button_ok" onclick="javascript:history.go(-1);"/>
			 		</div>
		  	</td>
		  </tr>
	</table>
	<hr>
	<br>
	<c:choose>
		<c:when test="${comments != null && fn:length(comments) != 0}">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="24" bgcolor="#353c44"><table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="6%" height="19" valign="bottom"><div align="center"><img src="${pageContext.request.contextPath }/images/tb.gif" width="14" height="14" /></div></td>
											<td width="94%" valign="bottom"><span class="STYLE1">显示请假申请的批注信息</span></td>
										</tr>
									</table></td>
									<td><div align="right"><span class="STYLE1">
			              </span></div></td>
								</tr>
							</table></td>
						</tr>
					</table></td>
				</tr>
				<tr>
					<td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#a8c7ce" onmouseover="changeto()"  onmouseout="changeback()">
						<tr>
							<td width="15%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">时间</span></div></td>
							<td width="10%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">批注人</span></div></td>
							<td width="75%" height="20" bgcolor="d3eaef" class="STYLE6"><div align="center"><span class="STYLE10">批注信息</span></div></td>
						</tr>
						<c:if test="${comments != null && fn:length(comments) != 0}">
							<c:forEach items="${comments}" var="comment">
								<tr>
									<td height="20" bgcolor="#FFFFFF" class="STYLE6"><div align="center"><fmt:formatDate value="${comment.time}" pattern="yyyy-MM-dd HH:mm:ss"/></div></td>
									<td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${comment.userId}</div></td>
									<td height="20" bgcolor="#FFFFFF" class="STYLE19"><div align="center">${comment.fullMessage}</div></td>
								</tr>
							</c:forEach>
						</c:if>
					</table></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr>
					<td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="24" bgcolor="#F7F7F7"><table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="6%" height="19" valign="bottom"><div align="center"><img src="${pageContext.request.contextPath }/images/tb.gif" width="14" height="14" /></div></td>
											<td width="94%" valign="bottom"><span><b>暂时没有批注信息</b></span></td>
										</tr>
									</table></td>
								</tr>
							</table></td>
						</tr>
					</table></td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	
</body>
</html>