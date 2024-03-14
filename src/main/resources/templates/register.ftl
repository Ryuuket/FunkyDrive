<html>
<#include "style/registercss.ftl">
     <title>Driver Application - Authentication</title>
<body>
<div class="form-header">
    <h1>Register</h1>
</div>

    <form action="/register" method="post">
    <div class="content-body">
    <div class="content-names">

    <div class="content-name">
        <label for="Name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>
    </div>

    <div class="content-name">
        <label for "Last name"> Last name:</label>
        <input type="text" id="lastName" name="last name" required><br><br> <#-- for break the line -->

    </div>

</div>

    <div class="content-email-password">
        <label for="email">email:</label>
        <input type="email" id="email" name="email" required><br><br>
         <#if error == "email">
                    <p>Email must be provided.</p>
                </#if>
        <#if error == "invalidEmail">
            <p>This email doesn't exist</p>
        </#if>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        <#if error == "password">
        <p>The password must have at list one alphabetical character, one digit and be at least 8 characters long.</p>
        </#if>

        <label for="confirm password">Confirm Password:</label>
        <input type="password" id="confirmPassword" name="confirm password" required><br><br>
        <#if error == "passwordMismatch">
            <p>This password must be the same as the previous one.</p>
        </#if>
        <button type="submit">Login</button>
</div>

</div>


    </div>

    </form>
</body>
</html>
