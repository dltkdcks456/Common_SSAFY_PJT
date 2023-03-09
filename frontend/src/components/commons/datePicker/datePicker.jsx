/* eslint-disable */
import DatePicker from 'react-datepicker';
import { ko } from 'date-fns/esm/locale';
import './datePicker.css';

export default function DatePick({ startTime, setStartTime }) {
  return (
    <DatePicker
      locale={ko}
      selected={startTime}
      onChange={date => setStartTime(date)}
      className="input-datepicker"
      showTimeSelect
      dateFormat="yyyy-MM-dd HH:mm:ss"
      minDate={new Date()}
      width="12rem"
    />
  );
}
