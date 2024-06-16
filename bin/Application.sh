#!/usr/bin/env bash

get_absolute_path() {
    case "$1" in
        /*) path="$1";;
        *)  path="$(pwd)/$1";;
    esac
    realpath "${path}"
}

get_application_dir() {
  local bin_dir=$(get_absolute_path "$(dirname "$1")")
  dirname "${bin_dir}"
}

get_application_file() {
  local startup_script=$(basename "$1")
  echo ${startup_script/sh/java}
}

app_dir=$(get_application_dir "$0")
app_file=$(get_application_file "$0")

APP_DIR=${app_dir} "${app_dir}/src/${app_file}" "$@"
