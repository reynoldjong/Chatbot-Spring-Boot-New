<!DOCTYPE html>
<html lang="en">
<head>
    <title>Rating and Comments</title>
</head>
<body>
<center><b>
<h2>Feedback Form</h2>
<form action="/feedback" method="post">

How do you rate the Chatbot?
    <input type="radio" name="rating" value="1" required /> Hate
    <input type="radio" name="rating" value="2" /> Dislike
    <input type="radio" name="rating" value="3" /> Neutral
    <input type="radio" name="rating" value="4" /> Like
    <input type="radio" name="rating" value="5" /> Love
<br /><br />

Any extra comments?
	<textarea name="comments" rows="6" cols="40"></textarea><BR><BR>

<input type="submit" value="Send">
<input type="reset" value="Clear">
</form>
</body>
</html>