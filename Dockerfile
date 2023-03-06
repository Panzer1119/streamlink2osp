FROM lscr.io/linuxserver/ffmpeg:5.1.2

# Install the required packages
RUN apt update && apt install -y streamlink && rm -rf /var/lib/apt/lists/* /var/tmp/*

# Copy script to container and make it executable
COPY streamlink2osp.sh /
RUN chmod +x /streamlink2osp.sh

# Run script as main program
CMD ["/streamlink2osp.sh"]
