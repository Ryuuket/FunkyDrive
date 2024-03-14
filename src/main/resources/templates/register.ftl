<html>
<head>
    <title>Driver Application - Registration</title>
    <#include "style/login.ftl">
</head>
<body>
    <h2>Registration</h2>
    <form action="/register" method="post">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required/>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required/>

        <button type="submit">Register</button>
    </form>
</body>
</html>
