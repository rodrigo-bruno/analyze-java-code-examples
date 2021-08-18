#!/usr/bin/python

import matplotlib.pyplot as plt
import numpy as np

vanilla =          np.loadtxt("mapwrite-vanilla.dat", dtype=float)
user_specialized = np.loadtxt("mapwrite-user-specialized.dat", dtype=float)

fig = plt.figure()
plt.plot(user_specialized, label='User-Specialized')
plt.plot(vanilla, label = 'Vanilla')
plt.legend()
fig.savefig("mapwrite-all.png")

vanilla          = vanilla[40000:80000]
user_specialized = user_specialized[40000:80000]

print("mean(vanilla): ",          np.mean(vanilla))
print("mean(user_specialized): ", np.mean(user_specialized))

print("median(vanilla): ",          np.median(vanilla))
print("median(user_specialized): ", np.median(user_specialized))

print("p99(vanilla): ",             np.percentile(vanilla, 99))
print("p99(user_specialized): ",    np.percentile(user_specialized, 99))

vanilla[vanilla > np.percentile(vanilla, 99)] = np.nan
user_specialized[user_specialized > np.percentile(user_specialized, 99)] = np.nan

print("final mean (vanilla): ",          np.nanmean(vanilla))
print("final mean (user_specialized): ", np.nanmean(user_specialized))

tput_vanilla          = 10000/vanilla*1000
tput_user_specialized = 10000/user_specialized*1000
print("final mean tput (vanilla): "         , np.nanmean(tput_vanilla))
print("final mean tput (user_specialized): ", np.nanmean(tput_user_specialized))
print("final std tput (vanilla): ",          np.nanstd(tput_vanilla))
print("final std tput (user_specialized): ", np.nanstd(tput_user_specialized))

fig = plt.figure()
plt.plot(user_specialized, label='User-Specialized')
plt.plot(vanilla, label = 'Vanilla')
plt.ylim(ymin=0)
plt.ylabel('Time (ns) for 1k ops')
plt.xlabel('Samples')
plt.legend()
fig.savefig("mapwrite-warmed.png")


