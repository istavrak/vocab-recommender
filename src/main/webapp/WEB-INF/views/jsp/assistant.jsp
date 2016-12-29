<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Vocab-recommender<c:if test="${not empty version}"> - v${version}</c:if></title>

<spring:url value="/resources/css/core.css" var="coreCss" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="${coreCss}" rel="stylesheet" />
<script src="https://cdn.rawgit.com/google/code-prettify/master/loader/run_prettify.js"></script>
</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
	<div class="navbar-header">
		<a class="navbar-brand" href="#">Vocab-recommender<c:if test="${not empty version}"> - v${version}</c:if></a>
	</div>
  </div>
</nav>
<script>
function searchFormSelect() {
     var searchType = document.getElementById("searchType");
     var selectedValue = searchType.options[searchType.selectedIndex].title;
     document.getElementById("inputTitle").innerHTML = selectedValue;
}
</script>

<div class="jumbotron">
<div class="container">
	<div class="row">
		<div class="col-md-12">
		  <h2>Vocab-recommender API request</h2>
          <form class="form-horizontal" role="form" method="post" action="/assistant/recommend">
            <div class="form-group col-md-12">
              <label for="filter">Search</label>
              <select id="searchType" name="searchType" class="form-control form-custom" onchange="searchFormSelect();">
                  <option value="0" title="Webpage keywords (comma separated)" selected>Webpage keywords</option>
                  <option value="1" title="Webpage URL">Webpage URL</option>
              </select>
            </div>
            <div class="form-group col-md-12">
              <label for="input" id="inputTitle">Webpage keywords (comma separated)</label>
              <input class="form-control form-custom" id="inputField" name="inputField" type="text" />
            </div>
            <div class="form-group col-md-12">
              <label for="contain">Language</label>
              <select class="form-control form-custom">
                  <option value="0" selected>English - en</option>
              </select>
            </div>
            <div class="form-group col-md-12">
              <label for="static" class="static">Include multimedia terms</label>
                <input class="form-control form-custom static-box" name="static" id="static" type="checkbox" value="true" checked/>
            </div>
            <input type="submit" value="Search" class="btn btn-primary btn-lg"/>
          </form>
        </div>
	</div>
</div>
</div>

<div class="container">
<div class="row">
<div class="col-md-8">
    <c:if test="${not empty response}">
    <h2>Vocab-recommender API response</h2>
    <code <c:if test="${response.getSuccess()}">class="green"</c:if>>
    <c:if test="${response.getSuccess()}">Success</c:if>
    <c:if test="${not response.getSuccess()}">Fail</c:if>
    </code>
    </c:if>
    <div id="responseBox" class="responseBox">
    <c:if test="${not response.getSuccess()}">
    ${response.getMessage()}
    </c:if>
    <c:if test="${not empty response && response.getSuccess()}">
        <pre class="prettyprint">
        ${response.toJson()}
        </pre>
    </c:if>
    </div>
</div>
</div>
</div>
<div class="panel-footer" style="margin-top:20px;">
    <div class="container">
        <p>Copyright &copy; 2016 <a href="http://www.istavrak.com">Ioannis Stavrakantonakis<a/> -
        <a href="https://github.com/istavrak/vocab-recommender/blob/master/LICENSE.md">MIT License</a>.</p>
    </div>
</div>

</body>
</html>