<html>
<head>
    <#include "/style/login.css">
    <title>Driver Application - Authentication</title>
</head>
<body>
    <header>
       <#include "/components/header.ftl">
    </header>
    <form action="/login" method="post">
        <label for="email">mail:</label>
        <input type="email" id="email" name="email" required/>
        <#if error == "email">
            <p>Email must be provided.</p>
        </#if>

        <label for="password">password:</label>
        <input type="password" id="password" name="password" required/>
        <#if error == "password">
            <p>Missing password.</p>
        </#if>
        <button type="submit">log in</button>
    </form>
</body>
</html>