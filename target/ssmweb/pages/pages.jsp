<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="box-footer">
    <div class="pull-left">
        <div class="form-group form-inline">
            总共${pageInfo.pages} 页，共${pageInfo.pageSize}条数据。
            每页
            <select class="form-control" onchange="gotoPage(1,this.value)">
                <c:forEach var="num" begin="1" end="10">
                    <option value="${num}" ${pageInfo.pageSize == num ?  'selected' : ''}>${num}</option>
                </c:forEach>
            </select>
            条,
            跳到
            <select class="form-control" onchange="gotoPage(this.value,${pageInfo.pageSize})">
                <c:forEach var="num" begin="1" end="${pageInfo.pages}">
                    <option value="${num}" ${pageInfo.pageNum == num ?  'selected' : ''} ">${num}</option>
                </c:forEach>
            </select>
            页
        </div>
    </div>

<%--    <div class="box-footer">--%>
<%--    <div>--%>
<%--        <div class="form-group form-inline">--%>
<%--            跳到--%>
<%--            <select class="form-control" onchange="gotoPage(1,this.value)">--%>
<%--                <c:forEach var="num" begin="1" end="${pageInfo.pages}">--%>
<%--                    <option value="${num}" ${pageInfo.pageNum == num ?  'selected' : ''} href="javascript:gotoPage(${num},${pageInfo.pageSize})">${num}</option>--%>
<%--                </c:forEach>--%>
<%--            </select>--%>
<%--            页--%>
<%--        </div>--%>
<%--    </div>--%>



    <div class="box-tools pull-right">
        <ul class="pagination">
            <li><a href="javascript:gotoPage(1,'${pageInfo.pageSize}')" aria-label="Previous">首页</a></li>
            <li><a href="javascript:gotoPage(${pageInfo.pageNum-1},${pageInfo.pageSize})">上一页</a></li>
            <%--处理页码--%>
            <c:forEach var="num"
                       begin="${pageInfo.pageNum-5 > 0 ? pageInfo.pageNum-5 : 1}"
                       end="${pageInfo.pageNum+5 < pageInfo.pages ? pageInfo.pageNum+5 : pageInfo.pages}">
                <li>
                    <a href="javascript:gotoPage(${num},${pageInfo.pageSize})">
                        <c:choose>
                            <c:when test="${pageInfo.pageNum == num}">
                                    <span style="color: red">
                                            ${num}
                                    </span>
                            </c:when>
                            <c:otherwise>${num}</c:otherwise>
                        </c:choose>

                    </a>
                </li>
            </c:forEach>

            <li><a href="javascript:gotoPage(${pageInfo.pageNum+1},${pageInfo.pageSize})">下一页</a></li>
            <li><a href="javascript:gotoPage(${pageInfo.pages},${pageInfo.pageSize})" aria-label="Next">尾页</a></li>

        </ul>
    </div>
</div>

<script type="text/javascript">
    /**
     * 换页的方法
     * pageNum  当前页
     * pageSize 页大小
     */
    function gotoPage(pageNum,pageSize) {
        // 完整地址：http://localhost:8080/user/findByPage?pageNum=1&pageSize=5
        //var url = "/user/findByPage";

        var url = "${param.pageUrl}";
        location.href = url + "?pageNum="+pageNum + "&pageSize="+pageSize;
    }
</script>
