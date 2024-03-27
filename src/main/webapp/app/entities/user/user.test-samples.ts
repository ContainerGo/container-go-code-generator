import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: '3b293e0f-f485-4536-999c-c615daddf6e1',
  login: 'P@uNY\\2Jenz\\\\TA5ZTe\\VP2',
};

export const sampleWithPartialData: IUser = {
  id: 'f77810c3-5c8c-4591-a326-c299c5fc7546',
  login: 'jT26lS',
};

export const sampleWithFullData: IUser = {
  id: 'f67243f4-28ca-4420-8c09-50d60a62aad7',
  login: 'X$d_h@dk9\\gKTwArN\\wpwGqn\\|YdXz',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
