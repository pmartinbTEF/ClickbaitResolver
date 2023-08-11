#!/bin/sh -l
echo "::debug ::Debug message"

echo "Hello $1"
time=$(date)
echo "time=$time" >> $GITHUB_OUTPUT
echo $time
echo "::group::Some expandable logs"
echo "Some logs"
echo "Some logs"
echo "Some logs"
echo "::endgroup::"