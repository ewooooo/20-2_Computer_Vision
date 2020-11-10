# # from tensorflow.python.client import device_lib
# #
# # print(device_lib.list_local_devices())
# #
# import tensorflow as tf
#
# assert tf.test.is_gpu_available()
# #assert tf.test.is_built_with_cuda()
#
# # import tensorflow as tf
# # print(tf.test.is_built_with_cuda())
# #
# # tf = tf.Session(config=tf.ConfigProto(log_device_placement=True))
# # tf.list_devices()
# #
# # import tensorflow

import tensorflow as tf
from tensorflow.python.client import device_lib
print(device_lib.list_local_devices())
print(tf.__version__)
print("Num GPUs Available: ", len(tf.config.experimental.list_physical_devices('GPU')))
print(tf.test.is_gpu_available())