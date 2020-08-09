FROM alpine
COPY . /producer
RUN apk update && apk add \
&& apk --no-cache add openjdk11 --repository=http://dl-cdn.alpinelinux.org/alpine/edge/community \
&& apk add maven


