import moment from 'moment';

export default function formatDate(timestamp, format = 'YYYY-MM-DD HH:mm') {
    return moment(timestamp).format(format);
}
