<?php
$frequency = 5; // seconds
$etag = floor(time() / $frequency) * $frequency;
header('Etag: ' . $etag);

usleep(250000); // delay as if "server is working", so it's easier to see what's going on at client-side.

$etagMatches = isset($_SERVER['HTTP_IF_NONE_MATCH']) && trim($_SERVER['HTTP_IF_NONE_MATCH']) == $etag;
if ($etagMatches) {
    header("ETag Matches", true, 304);
    exit;
}

header('Cache-Control: public, max-age=31556926'); // cache "forever" (1 year in seconds)

usleep(1000000); // delay as if "server is working", so it's easier to see what's going on at client-side.

// generate image with ETag on it
$im = imagecreatetruecolor(100, 16); // x,y
$text_color = imagecolorallocate($im, 0, 255, 0); // rgb
imagestring($im, 5, 0, 0, $etag, $text_color); // size,x,y

// send generated image
header('Content-Type: image/png');
imagepng($im);

imagedestroy($im);
?>
