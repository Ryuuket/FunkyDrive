<style type="text/css">
    html {
        display: flex;
        flex-flow: column;
    }

    body {
        background-color: #E4CCFE;
    }

    body h2 {
        display: flex;
        justify-content: flex-end;
    }

    form {
        display: flex;
        flex-flow: column;
        width: 296px;
        margin: 0 auto;
        padding: 2px;
    }

    form input {
        display: block;
        width: 100%;
        margin-bottom: 10px;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    form button {
        align-self: center;
        width: 60%;
        padding: 10px;
        background-color: black;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin-top: 5px;
    }

    form button:hover {
        background-color: #45a049;
    }

    form p {
        color: red;
    }

    @media only screen and (max-width: 300px) {
        form {
                width: 100%;
        }

        body h2{
            font-size: 10vw;
        }
    }
</style>