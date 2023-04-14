<?php
	include "connect.php";
	$mangSP = array();
	$page = $_GET['page'];
	$idSP = $_POST['idSP'];
	$spage = 5;
	$limit = ($page - 1) * $spage;
	$query = "SELECT * FROM sanpham WHERE idSP = $idSP LIMIT $limit,$spage";
	$data = mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($mangSP, $row);
	}
	echo json_encode($mangSP);

?>