<html>
<head>
    <title>Driver Application - Authentication</title>
    <style>
        /* Style the form container */
        .form-container {
            width: 300px;
            margin: 0 auto;
        }

        /* Style the form header */
        .form-header {
            text-align: center;
            margin-bottom: 20px;
        }

        /* Style the form inputs and labels */
        .form-input {
            display: block;
            width: 100%;
            margin-bottom: 10px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        /* Style the submit button */
        .submit-button {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        /* Style the submit button on hover */
        .submit-button:hover {
            background-color: #45a049;
        }

    </style>
</head>
<body>
    <h1>Authentication</h1>
    <form action="/login" method="post">
        <label for="Name">Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for "Last name"> Last name:</label>
        <input type="text" id="last name" name="last name" required><br><br> <#-- for break the line -->

        <label for="email">email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <label for="confirm password">Confirm Password:</label>
        <input type="confirm password" id="confirm password" name="confirm password" required><br><br>

        <button type="submit">Login</button>
    </form>
</body>
</html>
