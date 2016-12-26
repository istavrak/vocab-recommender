<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Vocab-recommender<c:if test="${not empty version}"> - v${version}</c:if></title>

<spring:url value="/resources/css/core.css" var="coreCss" />
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link href="${coreCss}" rel="stylesheet" />
</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
	<div class="navbar-header">
		<a class="navbar-brand" href="#">Vocab-recommender<c:if test="${not empty version}"> - v${version}</c:if></a>
	</div>
  </div>
</nav>

<div class="jumbotron">
  <div class="container">
	<h1>Vocabulary discovery assistant</h1>
    <p>An approach to help developers construct the vocabulary that covers the needs of a webpage.</p>
    <p>Details about the endpoints can be found both here and at the corresponding <a
    href="https://github.com/istavrak/vocab-recommender" role="button">github repo</a>.
    Click on the following button to learn more information about the project or simply use the GET endpoints
    described below.</p>
    <p>
		<a class="btn btn-primary btn-lg" href="http://istavrak.com/vocab-recommender/" role="button">Learn more</a>
	</p>
	</div>
</div>

<div class="container">

  <div class="row">
	<div class="col-md-4">
		<h2>API Endpoints</h2>
		<p>
		<ul>
		    <li><code>GET /recommendation?url=&lt;URL&gt;</code></li>
		    <li><code>GET /recommendation?query=&lt;keyword1,keyword2&gt;</code></li>
		    <li><code>GET /recommendation/static</code></li>
		</ul>
		</p>
	</div>
	<div class="col-md-4">
    		<h2>Assistant UI</h2>
    		<p>Use the API via a user friendly User Interface.</p>
    		<p>
    			<a class="btn btn-default" href="/assistant" role="button">
    			<span class="fa fa-search">&nbsp;</span>Search</a>
    		</p>
    </div>
	<div class="col-md-4">
		<h2>Source code</h2>
		<p>Fork the project on github.</p>
		<p>
			<a class="btn btn-default" href="https://github.com/istavrak/vocab-recommender" role="button">
			<span class="fa fa-github">&nbsp;</span>Download</a>
		</p>
	</div>
  </div>
</div>

<div class="panel-footer" style="margin-top:20px;">
    <div class="container">
        <p>Copyright &copy; 2016 <a href="http://www.istavrak.com">Ioannis Stavrakantonakis<a/> -
        <a href="https://github.com/istavrak/vocab-recommender/blob/master/LICENSE.md">MIT License</a>.</p>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</body>
</html>