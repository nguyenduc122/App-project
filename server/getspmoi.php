<?php
	include "connect.php";
	$mangspmoi = array();
	$query = "SELECT *FROM sanpham ORDER BY ID DESC LIMIT 6";
	$data = mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangspmoi, $row);
	}
		echo json_encode($mangspmoi);
	
?>