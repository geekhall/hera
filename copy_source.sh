#!/bin/zsh
workdir=`pwd`
src_dir=${workdir}/generator/src/main/java/cn/geekhall/hera/server/
dst_dir=${workdir}/server/src/main/java/cn/geekhall/hera/server/
echo $src_dir;
echo $dst_dir;

cp -rf $src_dir/* $dst_dir/