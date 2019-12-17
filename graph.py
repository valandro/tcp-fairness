import matplotlib.pyplot as plt
import matplotlib.animation as animation
from matplotlib.lines import Line2D
import argparse
import numpy as np

fig = plt.figure()
ax1 = fig.add_subplot(3,1,1)
ax2 = fig.add_subplot(3,1,2)
ax3 = fig.add_subplot(3,1,3)
ax = [ax1,ax2,ax3]
line = [Line2D([],[],color='blue'),Line2D([],[],color='red'),Line2D([],[],color='green')]
data = [[],[],[]]
x = [[],[],[]]
y = [[],[],[]]

def animate(frame):
    for i in range(3):
        x[i].append(frame)
        y[i].append(data[i][frame])
        line[i].set_data(x[i], y[i])

    return line

def init():
    for i in range(3):
        line[i].set_data([],[])
    return line


def graph(files):
    global data, line, ax

    for i, file in enumerate(files):
        file_c = open(file,'r').read()
        print(file_c.split(' Mbit/s'))
        data[i] = [int(x) for x in file_c.split(' Mbit/s\n') if x != '']

    for i in range(len(files)):
        ax[i].set_xlim(0,int(len(data[i])))
        ax[i].set_ylim(0,int(max(max(data))))
        ax[i].add_line(line[i])
        line[i].set_data([],[])

        ax[i].set_xlabel('Time (seconds)')
        ax[i].set_ylabel('Bandwidth (Mbit/s)')
        ax[i].set_title('TCP fairness experiment')
        ax[i].grid()

    ani = animation.FuncAnimation(fig, animate, init_func=init, frames=np.arange(0,len(data[0])), blit=True, interval=200, repeat=False)
    #plt.show()
    ani.save('../results/res_experiment.gif', writer='pillow', fps=4)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Plotter of bandwidth test.')
    parser.add_argument('-f', '--file', nargs='+', type=str, help='log file', required=True)
    args = parser.parse_args()

    graph(args.file)
