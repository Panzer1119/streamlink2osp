FROM rayou/streamlink:5.3.1

# Install curl and jq and remove cache
RUN apk add --no-cache curl jq

# Copy script to container and make it executable
COPY streamlink2osp.sh /
RUN chmod +x /streamlink2osp.sh

# Remove the entrypoint from the base image
ENTRYPOINT []

# Run script as main program
CMD ["sh", "/streamlink2osp.sh"]
