Multithreaded TCP Client Server Program Service written in Java. 'New Hopes' Charity Association To-Do List

TCP Server Output Sample (3 Clients Connected)
```
A new client is connected : Socket[addr=/192.168.0.192,port=56779,localport=1234]
Assigning new thread for this client
A new client is connected : Socket[addr=/192.168.0.192,port=56780,localport=1234]
Assigning new thread for this client
A new client is connected : Socket[addr=/192.168.0.192,port=56781,localport=1234]
Assigning new thread for this client
```

TCP Client Sample Output (event added for specified certain date)
```
New Hopes Charity Association To-Do List
Add or list tasks for a specific date...
Type Stop to terminate connection.

add; 20 May 2024; Fireworks Display
SERVER > 20 May 2024; Fireworks Display
```

TCP Client Sample Output (event listed for specified certain date)
```
New Hopes Charity Association To-Do List
Add or list tasks for a specific date...
Type Stop to terminate connection.

list; 20 May 2024
SERVER > 20 May 2024; Fireworks Display
```

TCP Client Sample Output (Invalid Action used)
```
New Hopes Charity Association To-Do List
Add or list tasks for a specific date...
Type Stop to terminate connection.

display; 20 May 2023
SERVER > Invalid action used
```

TCP Client Sample Output (Terminate Connection)
```
New Hopes Charity Association To-Do List
Add or list tasks for a specific date...
Type Stop to terminate connection.

stop
SERVER > TERMINATE
Closing this connection : Socket[addr=/192.168.0.192,port=1234,localport=56779]
```
