#!/bin/sh -l
echo "::debug ::Debug message"

echo "Hello $1"
time=$(date)
echo "::set-output name=time::$time"
echo "::group::Some expandable logs"
echo "Some logs"
echo "Some logs"
echo "Some logs"
echo "::endgroup::"