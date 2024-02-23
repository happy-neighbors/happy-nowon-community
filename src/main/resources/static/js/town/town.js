/**
 * 
 */

function update(){
	//document.getElementById('detail-view').style.display='none';
	const element = document.querySelector(".original");
	element.style.display="none";
	const updateView = document.querySelector(".update");
	updateView.style.display="block";
}
function cancel(){
	const element = document.querySelector(".original");
	element.style.display="block";
	const updateView = document.querySelector(".update");
	updateView.style.display="none";
}