import matplotlib.pyplot as plt
import matplotlib.animation as animation


def get_coord(path):
    graph_data = open(path, 'r').read()
    lines = graph_data.split('\n')
    xs = [[], [], []]
    ys = [[], [], []]

    clients = []

    for line in lines:
        if len(line) > 1:
            time, client, bandwith = line.split(',')
            if client in clients:
                ys[clients.index(client)].append(float(bandwith))
                xs[clients.index(client)].append(float(time))
            else:
                clients.append(client)
                ys[clients.index(client)].append(float(bandwith))
                xs[clients.index(client)].append(float(time))
    return xs, ys, clients


def animate(i):
    xs, ys, clients = get_coord('metrics.csv')
    plt.cla()
    for c in range(len(clients)):
        plt.plot(xs[c], ys[c], label='Client '+str(clients[c]))
    plt.legend(loc='upper center')
    plt.title('TCP Fairness Experiment')
    plt.xlabel('Time (seconds)')
    plt.ylabel('Throughput (M bits/s)')


ani = animation.FuncAnimation(plt.gcf(), animate, interval=1000)  # update every 1sec (1000)

plt.tight_layout()
plt.show()

