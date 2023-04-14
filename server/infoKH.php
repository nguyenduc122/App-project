<?php
	include "connect.php";
	$tenKhachHang = $_POST['tenKH'];
	$soDienThoai = $_POST['SDT'];
	$email = $_POST['email'];
	if(strlen($tenKhachHang) > 0 && strlen($email) > 0 && strlen($soDienThoai) > 0){
		$query = "INSERT INTO donhang(id, tenKH, SDT, email) VALUES (null, '$tenKhachHang', '$soDienThoai', '$email')";
		if(mysqli_query($conn, $query)){
			$idDonHang = $conn->insert_id;
			echo $idDonHang;
		}else{
			echo "That bai";

		}

	}else{
		echo "Ban hay kiem tra lai du lieu";
	}

?>