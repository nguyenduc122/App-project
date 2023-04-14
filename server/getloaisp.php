<?php
	include "connect.php";
	$query = "SELECT * FROM loaisp";
	$data = mysqli_query($conn,$query);
	$mangloaisp = array();
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangloaisp,$row);
	}
	echo json_encode($mangloaisp);
	
?>