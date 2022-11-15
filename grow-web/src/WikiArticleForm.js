import React from 'react';

class WikiArticleForm extends React.Component {

  daysInMonth = {
    "01" : 31,
    "02" : 28,
    "03" : 31,
    "04" : 30,
    "05" : 31,
    "06" : 30,
    "07" : 31,
    "08" : 31,
    "09" : 30,
    "10" : 31,
    "11" : 30,
    "12" : 31
  };

  constructor(props) {
    super(props);
    this.state = {
      articleName: '',
      month: '0',
      year:'0',
      day:'0',
      mostViewedDayOfMonth:'',
      topArticles: []
    };

    this.handleArticleNameChange = this.handleArticleNameChange.bind(this);
    this.handleYearChange = this.handleYearChange.bind(this);
    this.handleMonthChange = this.handleMonthChange.bind(this);
    this.handleDayChange = this.handleDayChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleSubmitTopArticles = this.handleSubmitTopArticles.bind(this);
  }

  handleArticleNameChange(event) {
    this.setState({articleName: event.target.value});
  }

  handleMonthChange(event) {
    this.setState({month: event.target.value});
  }

  handleYearChange(event) {
    this.setState({year: event.target.value});
  }

  handleDayChange(event) {
    this.setState({day: event.target.value});
  }

  handleSubmitTopArticles(event) {
    event.preventDefault();

    if (this.state.month === "0") {
      alert("You must chose a month");
      return;
    } else if (this.state.year === "0") {
      alert("You must chose a year");
      return;
    }

    if (this.state.day !== "0") {
      this.fetchTopArticlesForWeek();
    } else {
      this.fetchTopArticlesForMonth();
    }
  }

  handleSubmit(event) {
    event.preventDefault();

    // validate form
    if (this.state.articleName.length === 0) {
      alert("You must fill out the article name");
      return;
    } else if (this.state.month === "0") {
      alert("You must chose a month");
      return;
    } else if (this.state.year === "0") {
      alert("You must chose a year");
      return;
    }

    this.fetchMostViewedDayOfMonth();
    this.fetchArticleViewCountForMonth();
    if (this.state.day !== "0") {
      this.fetchArticleViewCountForWeek();
    }
  }

  fetchTopArticlesForMonth() {
    var url = `/wiki/top/year/${this.state.year}/month/${this.state.month}`;
    fetch(url)
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({topArticles: result.articles});
        },
        (error) => {
          console.log(error);
        }
      )
      .catch((error) => {console.log(error)});
  }

  fetchTopArticlesForWeek() {
    var url = `/wiki/top/year/${this.state.year}/month/${this.state.month}/week_start/${this.state.day}`;
    fetch(url)
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({topArticles: result.articles});
        },
        (error) => {
          console.log(error);
        }
      )
      .catch((error) => {console.log(error)});
  }

  fetchMostViewedDayOfMonth() {
    var url = `/wiki/article/${this.state.articleName.trim()}/topDay/year/${this.state.year}/month/${this.state.month}`;
    fetch(url)
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({mostViewedDayOfMonth: result.dayOfMonthTimestamp});
        },
        (error) => {
          console.log(error);
        }
      )
      .catch((error) => {console.log(error)});
  }

  fetchArticleViewCountForMonth() {
    var url = `/wiki/article/${this.state.articleName.trim()}/stats/year/${this.state.year}/month/${this.state.month}`;
    fetch(url)
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({articleMonthViewCount: result.viewCount});
        },
        (error) => {
          console.log(error);
        }
      )
      .catch((error) => {console.log(error)});
  }

  fetchArticleViewCountForWeek() {
    var url = `/wiki/article/${this.state.articleName.trim()}/stats/year/${this.state.year}/month/${this.state.month}/week_start/${this.state.day}`;
    fetch(url)
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({articleWeekViewCount: result.viewCount});
        },
        (error) => {
          console.log(error);
        }
      )
      .catch((error) => {console.log(error)});
  }

  isLeapYear() {
    var year = this.state.year;
    if (year % 100 === 0) {
      if (year % 400 === 0) {
        return true;
      }
      return false;
    }
    if (year % 4 === 0) {
      return true;
    }
    return false;
  }

  isLeapMonth() {
    return this.state.month === "02" && this.isLeapYear();
  }

  render() {
    var days = [];
    var articles = [];
    if (this.state.month !== "0") {
      days.push(<option key="0" value="0">--Day--</option>);
      for (var i = 1 ; i <= this.daysInMonth[this.state.month] + (this.isLeapMonth() ? 1 : 0); i++) {
        days.push(<option key={i} value={i}>{i}</option>);
      }
    }

    if (this.state.topArticles.length > 0) {
      this.state.topArticles.map((article) => {
        articles.push(<tr><td>{article.rank}</td><td>{article.article}</td><td>{article.views}</td></tr>)
      });
    }

    return (
    <div>
      <form onSubmit={this.handleSubmit}>
        <label>
          Article Name:
          <input type="text" value={this.state.articleName} onChange={this.handleArticleNameChange} />
        </label>

        <br/>
        <select value={this.state.year} onChange={this.handleYearChange}>
          <option value="0">--Year--</option>
          <option value="2016">2016</option>
          <option value="2017">2017</option>
          <option value="2018">2018</option>
          <option value="2019">2019</option>
          <option value="2020">2020</option>
          <option value="2021">2021</option>
          <option value="2022">2022</option>
        </select>
        <select value={this.state.month} onChange={this.handleMonthChange}>
          <option value="0">--Month--</option>
          <option value="01">Jan</option>
          <option value="02">Feb</option>
          <option value="03">Mar</option>
          <option value="04">Apr</option>
          <option value="05">May</option>
          <option value="06">Jun</option>
          <option value="07">Jul</option>
          <option value="08">Aug</option>
          <option value="09">Sep</option>
          <option value="10">Oct</option>
          <option value="11">Nov</option>
          <option value="12">Dec</option>
        </select>
        <select value={this.state.day} onChange={this.handleDayChange}>
          {days}
        </select>
        <br/>

        <input type="submit" value="Get article stats" />
      </form>
      <span id='articleMostViewedDayOfMonth'>
        Article most viewed date: {this.state.mostViewedDayOfMonth}
      </span>
      <br/>
      <span id='articleViewCountForMonth'>
        Article month view count: {this.state.articleMonthViewCount}
      </span>
      <br/>
      <span id='articleViewCountForWeek'>
        Article week view count: {this.state.articleWeekViewCount}
      </span>

      <br/>
      <br/>

      <form onSubmit={this.handleSubmitTopArticles}>
        <select value={this.state.year} onChange={this.handleYearChange}>
          <option value="0">--Year--</option>
          <option value="2016">2016</option>
          <option value="2017">2017</option>
          <option value="2018">2018</option>
          <option value="2019">2019</option>
          <option value="2020">2020</option>
          <option value="2021">2021</option>
          <option value="2022">2022</option>
        </select>
        <select value={this.state.month} onChange={this.handleMonthChange}>
          <option value="0">--Month--</option>
          <option value="01">Jan</option>
          <option value="02">Feb</option>
          <option value="03">Mar</option>
          <option value="04">Apr</option>
          <option value="05">May</option>
          <option value="06">Jun</option>
          <option value="07">Jul</option>
          <option value="08">Aug</option>
          <option value="09">Sep</option>
          <option value="10">Oct</option>
          <option value="11">Nov</option>
          <option value="12">Dec</option>
        </select>
        <select value={this.state.day} onChange={this.handleDayChange}>
          {days}
        </select>
        <br/>

        <input type="submit" value="Get top articles" />
      </form>
      <br/>
      <table>
        <tr>
          <th>Rank</th>
          <th>Article</th>
          <th>Views</th>
        </tr>
        {articles}
      </table>
      {articles}
    </div>
    );
  }
}

export default WikiArticleForm;
