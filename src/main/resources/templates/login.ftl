<html>
<head>
    <title>Driver Application - Authentication</title>
    <#include "style/login.ftl">
</head>
<body>
    <h2>Authentication</h2>
    <form action='/submit-login'>
        <label for="email">mail:</label>
        <input type="email" required/>

        <label for="password">password:</label>
        <input type="password" required/>
        <button type="submit">log in</button>
    </form>
</body>
</html>