#!/bin/sh -l
echo "::debug ::Debug message"

echo "Hello4 $1"
otheroutput=$(date)
echo "time=otheroutput" >> $GITHUB_OUTPUT
echo $otheroutput
echo "::group::Some expandable logs"
echo "Some logs"
echo "Some logs"
echo "Some logs"
echo "::endgroup::"