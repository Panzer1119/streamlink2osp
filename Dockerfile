FROM rayou/streamlink:5.3.1

# Copy script to container and make it executable
COPY streamlink2osp.sh /
RUN chmod +x /streamlink2osp.sh

# Run script as main program
CMD ["/streamlink2osp.sh"]
