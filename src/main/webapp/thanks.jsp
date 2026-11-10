<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Murach's Java Servlets and JSP</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <h1>Thanks for taking our survey!</h1>

        <p>Here is the information that you entered:</p>

        <label>Email:</label>
        <span>${survey.user.email}</span><br>

        <label>First Name:</label>
        <span>${survey.user.firstName}</span><br>

        <label>Last Name:</label>
        <span>${survey.user.lastName}</span><br>

        <label>Date of Birth:</label>
        <span>${survey.user.birthDate}</span><br>

        <label>Heard From:</label>
        <span>${survey.heardFrom}</span><br>

        <label>Updates:</label>
        <span>${survey.wantsUpdates}</span><br>

        <label>Announcements:</label>
        <span>${survey.emailAnnouncements}</span><br>

        <label>Contact Via:</label>
        <span>${survey.contactVia}</span><br>

        <p>To enter another survey, click on the Back 
        button in your browser or the Return button shown 
        below.</p>

        <form action="survey" method="post">
            <input type="hidden" name="action" value="join">
            <input type="submit" value="Return" id="submit">
        </form>
    </body>
</html>
