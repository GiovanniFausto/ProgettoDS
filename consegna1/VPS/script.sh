echo sono nello script $1
mkdir -p var/videofiles/$1
 ffmpeg -i var/video/$1/video.mp4 \
 -map 0:v:0 -map 0:a\?:0 -map 0:v:0 -map 0:a\?:0 -map 0:v:0 -map 0:a\?:0 -map 0:v:0 -map 0:a\?:0 -map 0:v:0 -map 0:a\?:0 -map 0:v:0 -map 0:a\?:0 \
 -b:v:0 350k -c:v:0 libx264  -filter:v:0 "scale=320:-1" \
 -b:v:1 1000k -c:v:1 libx264  -filter:v:1 "scale=640:-1" \
 -b:v:2 3000k -c:v:2 libx264  -filter:v:2 "scale=1280:-1" \
 -b:v:3 245k -c:v:3 libvpx-vp9  -filter:v:3 "scale=320:-1" \
 -b:v:4 700k -c:v:4 libvpx-vp9  -filter:v:4 "scale=640:-1" \
 -b:v:5 2100k -c:v:5 libvpx-vp9  -filter:v:5 "scale=1280:-1" \
 -use_timeline 1 -use_template 1 -window_size 6 -adaptation_sets "id=0,streams=v  id=1,streams=a" \
 -hls_playlist true -f dash var/videofiles/$1/video.mpd
