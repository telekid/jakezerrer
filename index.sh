#!/bin/zsh

SCRIPT_FILE="$0"
SCRIPT_CONTENT=$(<"$SCRIPT_FILE")

http_response() {
    local content_length=$(wc -c < "$SCRIPT_FILE")
    printf "HTTP/1.1 200 OK\r\n"
    printf "Content-Type: text/plain\r\n"
    printf "Content-Length: %d\r\n" "$content_length"
    printf "Connection: close\r\n"
    printf "\r\n"
    printf "%s" "$SCRIPT_CONTENT"
}

handle_client() {
    while tcp_read; do
        line=${TCP_LINE%$'\r'}
        [[ -z "$line" ]] && break
    done
    
    tcp_send "$(http_response)"
    
    tcp_close
}

# Load zsh's TCP module and functions
zmodload zsh/net/tcp
autoload -U tcp_open tcp_read tcp_send tcp_close

# Clean up background processes on exit
cleanup() {
    jobs -p | xargs -r kill
    tcp_close -a
    exit 0
}
trap cleanup INT TERM

# Create listening socket once
ztcp -l 8082
server_fd=$REPLY

while true; do
    if tcp_open -a $server_fd; then
        handle_client
    fi
done
