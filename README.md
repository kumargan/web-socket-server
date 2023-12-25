## steps
1. Publisher thread pool that writes packet to websockets has queue of 500
2. Publisher thread pool size is 200
3. Publisher thread pool batch size. is each thread handles 100 connections.
4. So total concurrent websockets being handled will be 200*100 = 20000 conections
5. Hence limit per jvm is 20000 conections.

C4.large (2 core / 8 GB ) - 5000 limit ( 50 % utilization )