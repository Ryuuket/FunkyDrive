<html>

<#include "css/registercss.ftl">
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

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>


        <label for="confirm password">Confirm Password:</label>
        <input type="confirm password" id="confirmPassword" name="confirm password" required><br><br>
        <button type="submit">Login</button>
</div>

</div>


    </div>

    </form>
</body>
</html>
