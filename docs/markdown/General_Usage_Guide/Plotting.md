# Plotting

Koma has limited plotting support via xchart (JVM only for now). The basic functionality mimics that of
matplotlib and matlab:

```kotlin
// Create some normal random noise
var a = randn(100,2)
var b = cumsum(a)

figure(1)
// Second parameter is color
plot(a, 'b', "First Run")
plot(a+1, 'y', "First Run Offset")
xlabel("Time (s)")
ylabel("Magnitude")
title("White Noise")
```

An imshow command is also available for 2D plotting:

```kotlin
imshow(randn(100,100))
imshow(randn(100,100), BufferedImage.TYPE_BYTE_INDEXED)
```
