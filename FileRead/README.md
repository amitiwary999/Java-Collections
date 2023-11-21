# Single thread and multi thread time taken for set of tasks

I tried to do some set of tasks on single thread vs multi thread and find when does single thread perform betters and when multi thread. 

First I tried a very simple task that count the number of lines present in a paragraph. Single thread perform better here and my observation is that maybe the scheduling task is more expensive than executing its and multi thread scheduling task takes time.

But when we tried the same task but it has thread sleep of 1 millisecond to replicate CPU intensive task and there multi thread perform better than single thread because we were able to use all available core of the CPU.