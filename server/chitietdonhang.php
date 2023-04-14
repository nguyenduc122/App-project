<?php
	include "connect.php";
	$json = $_PORT['$json'];
	$data = json_decode($json);

	foreach ($data as $value) {
		$madonhang = $value['madonhang'];
		$masanpham = $value['masanpham'];
		$tensanpham = $value['tensanpham'];
		$giasanpham = $value['gisanpham'];
		$soluongsanpham = $value['soluongsanpham'];
		$query = "INSERT INTO chitietdonhang(id, madonhang, masanpham, tensanpham, gisanpham, soluongsanpham)
					VALUES (null, '$madonhang', '$masanpham', '$tensanpham', '$gisanpham', '$soluongsanpham')";
		// $Dta = mysqli_query($conn, $query);

		if (mysqli_query($conn, $query)) {
			echo "1";
		}else{
			echo "0";
		}
	}
	



?>