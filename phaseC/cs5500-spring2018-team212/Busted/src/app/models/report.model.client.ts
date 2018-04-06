

export class Report {
  _id: String;
  assignment1ID: String;
  assignment2ID: String;
  similarityscore: String;
  downloadLink: String;
  isResolved: Boolean;

  constructor (_id, assignment1ID, assignment2ID, similarityscore, downloadLink, isResolved) {
    this._id = _id;
    this.assignment1ID = assignment1ID;
    this.assignment2ID = assignment2ID;
    this.similarityscore = similarityscore;
    this.downloadLink = downloadLink;
    this.isResolved = isResolved;
  }
}
