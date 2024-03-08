<html>
<body>
<h1>Items:</h1>
<#list data.items as item>
    <h2>The item at index ${item?index} is ${item}</h2>
    <a href="localhost:8080/login">Login</a>
</#list>
</body>
</html>
