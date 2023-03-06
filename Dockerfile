FROM rayou/streamlink:5.3.1

# Copy script to container and make it executable
COPY streamlink2osp.sh /
RUN chmod +x /streamlink2osp.sh

# Remove the entrypoint from the base image
ENTRYPOINT []

# Run script as main program
CMD ["/streamlink2osp.sh"]
