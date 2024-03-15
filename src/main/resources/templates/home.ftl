<html>
<head>
    <#include  "/style/home.css">
    <title> Home Page </title>
</head>
<body>
  <header>
   <#include "/components/header.ftl">
    </header>
    <div class ="description">
        <h2> What is Funky-drive ? </h2>
        <p>Funky-drive is a cloud service that allows you to store your files and access them from anywhere. </p>
    </div>
    <div class ="choice">
        <h3> Sign Up or Login </h2>
        <div class="button">
            <div class="button1">
                <a href="/register">
                    <button type="submit">Sign Up </button>
                </a>
            </div>
            <div class="button2">
                 <a href="/login">
                    <button type="submit">Login</button>
                 </a>
            </div>
        </div>
    </div>
</body>
</html>