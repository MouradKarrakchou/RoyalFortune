<?php
$path = "../uploads/lastRun.png";
$file = $_POST['file'];
echo base64ToImage($file, $path);

function base64ToImage($base64_string, $output_file) {
    $file = fopen($output_file, "wb");

    $data = explode(',', $base64_string);

    fwrite($file, base64_decode($data[1]));
    fclose($file);

    return $output_file;
}