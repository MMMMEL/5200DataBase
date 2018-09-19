var voteDisplay = $("#voteNum").val();

$("#voteBtn").click(function() {
	alert("successfully voted" + voteDisplay);
	voteDisplay += 1;
})