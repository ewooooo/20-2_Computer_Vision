import json
import sys
import os
import numpy as np
import pdb
import xml.etree.ElementTree as ET
from tqdm import tqdm
import cv2
import os.path
#import matplotlib.pylab as plt
from sklearn.model_selection import train_test_split

def _make_file_list(path, data_type, status):
    f = open('{}_{}_filelist.txt'.format(status, data_type), 'w')

    for root, dirs, files in os.walk(path):
        rootpath = os.path.join(os.path.abspath(path), root)
        for num, file in enumerate(files):
            total_file = len(files)
            print('----[%d]:[%d]----'%(total_file,num))
            if data_type == 'img':
                if os.path.splitext(file)[1].lower() == '.jpg':
                    filepath = os.path.join(rootpath, file)
                    f.write(filepath+'\n')
            else:
                if os.path.splitext(file)[1].lower() == '.xml':
                    filepath = os.path.join(rootpath, file)
                    f.write(filepath + '\n')
    f.close()


def _compare(xml_list_file, imgs_list_file):
    xml_f = open(xml_list_file,'r')
    img_f = open(imgs_list_file,'r')

    xml_lines = xml_f.readlines()
    img_lines = img_f.readlines()

    xml_file_list = []
    img_file_list = []

    for i in range(0,len(xml_lines)):
        xml_file = xml_lines[i].split('\\')[-1][:-1]
        xml_file = xml_file.split('.')[0]
        xml_file_list.append(xml_file)

    for i in range(0,len(img_lines)):
        img_file = img_lines[i].split('\\')[-1][:-1]
        img_file = img_file.split('.')[0]
        img_file_list.append(img_file)

    for i in range(0, len(img_file_list)):

        if img_file_list[i] not in xml_file_list:
            print('NO   ',img_file_list[i])



def read_xml(filelist):
    all_imgs = []
    class_mapping = {}
    classes_count = {}

    f = open(filelist,'r')
    
    lines = f.readlines()
    lines = tqdm(lines)
    idx = 0
    for line in lines:
        lines.set_description("Processing %s"%line.split(os.sep)[-1])
        print()
        et = ET.parse(line[:-1])
        element=et.getroot()
        element_objs = element.findall('object')
        element_filename = element.find('filename').text+'.xml'
        element_imgfilename = element.find('filename').text+'.jpg'
        element_width = int(element.find('size').find('width').text)
        element_height = int(element.find('size').find('height').text)
        idx+=1

        if len(element_objs) > 0:
            annotation_data = {'filepath': os.path.join(imgs_path, element_imgfilename), 'width': element_width,'height': element_height, 'bboxes': []}
            annotation_data['image_id'] = idx

            for element_obj in element_objs:

                class_name = element_obj.find('name').text
                if class_name not in classes_count:
                    classes_count[class_name] = 1
                else:
                    classes_count[class_name] += 1

                # class mapping 정보 추가
                if class_name not in class_mapping:
                    class_mapping[class_name] = len(class_mapping)  # 마지막 번호로 추가

                obj_bbox = element_obj.find('bndbox')
                x1 = int(round(float(obj_bbox.find('xmin').text)))
                y1 = int(round(float(obj_bbox.find('ymin').text)))
                x2 = int(round(float(obj_bbox.find('xmax').text)))
                y2 = int(round(float(obj_bbox.find('ymax').text)))
                difficulty = int(element_obj.find('difficult').text) == 1
                annotation_data['bboxes'].append({'class': class_name, 'x1': x1, 'x2': x2, 'y1': y1, 'y2': y2, 'difficult': difficulty})
            all_imgs.append(annotation_data)

            '''
            #Visualize
            img = cv2.imread(annotation_data['filepath'])
            for bbox in annotation_data['bboxes']:
                cv2.rectangle(img, (bbox['x1'], bbox['y1']), (bbox['x2'], bbox['y2']), (0, 0, 255))
            cv2.imshow('img', img)
            cv2.waitKey(0)
            '''

    annotation_data_train, annotation_data_test = train_test_split(all_imgs, test_size=0.2, shuffle=True, random_state = 0)
    pdb.set_trace()

    return (annotation_data_train, annotation_data_test), all_imgs, classes_count, class_mapping

def get_imgidx(file):
    f = open(file, 'r')
    lines = f.readlines()
    for idx, line in enumerate(lines):
        img_to_idx[idx]=line

    return img_to_idx


if __name__ == "__main__":

    xml_test_path = "C:/Users/user/Desktop/Git_Project/20-2-AI-2-1_YOLO/misaeng_data/test/"
    imgs_test_path = "C:/Users/user/Desktop/Git_Project/20-2-AI-2-1_YOLO/misaeng_data/test_img/"

    xml_train_path = "C:/Users/user/Desktop/Git_Project/20-2-AI-2-1_YOLO/misaeng_data/trainval/"
    imgs_train_path = "C:/Users/user/Desktop/Git_Project/20-2-AI-2-1_YOLO/misaeng_data/trainval_img/"

    # Trainval Dataset
    _make_file_list(xml_train_path,"xml","trainval")
    _make_file_list(imgs_train_path,"img","trainval")

    # Test Dataset
    _make_file_list(xml_test_path,"xml","test")
    _make_file_list(imgs_test_path,"img","test")


