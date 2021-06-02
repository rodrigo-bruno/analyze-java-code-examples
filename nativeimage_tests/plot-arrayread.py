#!/usr/bin/python

import matplotlib.pyplot as plt
import numpy as np

vanilla =          np.loadtxt("arrayread-vanilla.dat", dtype=float)
user_specialized = np.loadtxt("arrayread-user-specialized.dat", dtype=float)

fig = plt.figure()
plt.plot(user_specialized, label='User-Specialized')
plt.plot(vanilla, label = 'Vanilla')
plt.legend()
fig.savefig("arrayread-all.png")

vanilla          = vanilla[40000:80000]
user_specialized = user_specialized[40000:80000]

print("mean(vanilla): ",          np.mean(vanilla))
print("mean(user_specialized): ", np.mean(user_specialized))

print("median(vanilla): ",          np.median(vanilla))
print("median(user_specialized): ", np.median(user_specialized))

print("p75(vanilla): ",             np.percentile(vanilla, 75))
print("p75(user_specialized): ",    np.percentile(user_specialized, 75))

vanilla[vanilla > np.percentile(vanilla, 75)] = np.nan
user_specialized[user_specialized > np.percentile(user_specialized, 75)] = np.nan

print("mean after correction(vanilla): ",          np.nanmean(vanilla))
print("mean after correction(user_specialized): ", np.nanmean(user_specialized))

fig = plt.figure()
plt.plot(user_specialized, label='User-Specialized')
plt.plot(vanilla, label = 'Vanilla')
plt.ylim(ymin=0)
plt.ylabel('Time (ns) for 1k ops')
plt.xlabel('Samples')
plt.legend()
fig.savefig("arrayready-warmed.png")


